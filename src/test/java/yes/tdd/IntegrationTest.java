package yes.tdd;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.Resource;
import java.io.File;
import java.net.URI;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("slow")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = TestContainersInitializer.class)
public abstract class IntegrationTest {
    protected TestResponse lastResponse;
    protected @Resource TestRestTemplate testRestTemplate;

    // TODO: 把@MockBean写在这里，让外部service例如sftp供全局集成测试调用

    @BeforeEach
    void setUp() {
        assertThat(TestContainersInitializer.isRunning()).isTrue();
    }

    protected TestResponse post(String urlTemplate, Object body, Object... vars) {
        return exchange(getRequest(urlTemplate, body, vars, HttpMethod.POST));
    }

    protected TestResponse put(String urlTemplate, Object body, Object... vars) {
        return exchange(getRequest(urlTemplate, body, vars, HttpMethod.PUT));
    }

    protected TestResponse delete(String urlTemplate, Object... vars) {
        return exchange(getRequest(urlTemplate, null, vars, HttpMethod.DELETE));
    }

    protected TestResponse get(String urlTemplate, Object... vars) {
        return get(urlTemplate, Map.of(), vars);
    }

    protected TestResponse get(String urlTemplate, Map<String, Object> params, MultiValueMap<String, String> headers) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(urlTemplate);
        params.forEach(builder::queryParam);
        URI uri = builder.build().encode().toUri();
        RequestEntity<?> request = new RequestEntity<>(headers, HttpMethod.GET, uri);
        return exchange(request);
    }

    protected TestResponse get(String urlTemplate, Map<String, Object> params, Object... vars) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(urlTemplate);
        params.forEach(builder::queryParam);
        URI uri = builder.buildAndExpand(vars).encode().toUri();
        RequestEntity<?> request = new RequestEntity<>(requestHeader(), HttpMethod.GET, uri);
        return exchange(request);
    }

    private RequestEntity<?> getRequest(String urlTemplate, Object body, Object[] vars, HttpMethod put) {
        URI uri = UriComponentsBuilder.fromUriString(urlTemplate).buildAndExpand(vars).encode().toUri();
        return new RequestEntity<>(body, requestHeader(), put, uri);
    }

    private TestResponse exchange(RequestEntity<?> request) {
        ResponseEntity<String> response = testRestTemplate.exchange(request, String.class);
        TestResponse result = new TestResponse(response);
        lastResponse = result;
        return result;
    }

    public TestResponse postFile(String urlTemplate, File file) {
        HttpHeaders headers = requestHeader();
        headers.setContentType(MediaType.parseMediaType("multipart/form-data"));
        MultiValueMap<String, Object> form = new LinkedMultiValueMap<>();
        if (file != null) {
            form.add("file", new FileSystemResource(file));
        }
        HttpEntity<MultiValueMap<String, Object>> files = new HttpEntity<>(form, headers);
        ResponseEntity<String> response = testRestTemplate.postForEntity(urlTemplate, files, String.class);
        TestResponse result = new TestResponse(response);
        lastResponse = result;
        return result;
    }

    protected HttpHeaders requestHeader() {
        return new HttpHeaders();
    }
}
