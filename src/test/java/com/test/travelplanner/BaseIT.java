package com.test.travelplanner;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.core.io.ResourceLoader.CLASSPATH_URL_PREFIX;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UncheckedIOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.FileCopyUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class BaseIT {

  @Autowired
  protected ResourceLoader resourceLoader;

  @Autowired
  protected ObjectMapper objectMapper;

  @Autowired
  protected MockMvc mockMvc;

  protected  <T> T readExpectedResponse(final String path, Class<T> responseType)
      throws JsonProcessingException {
    return objectMapper.readValue(readExpectedResponse(path), responseType);
  }

  protected String readExpectedResponse(final String path) {
    return readJsonFromFile("mock-response/" + path);
  }

  protected String readMockRequest(final String path) {
    return readJsonFromFile("mock-request/" + path);
  }
  private String readJsonFromFile(final String path) {
    try (Reader reader = new InputStreamReader(getResourceAsInputStream(path + ".json"), UTF_8)) {
      return FileCopyUtils.copyToString(reader);
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }

  private InputStream getResourceAsInputStream(final String path) throws IOException {
    return resourceLoader.getResource(CLASSPATH_URL_PREFIX + path).getInputStream();
  }
}
