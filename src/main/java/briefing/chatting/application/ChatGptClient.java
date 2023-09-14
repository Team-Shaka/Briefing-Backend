package briefing.chatting.application;

import briefing.chatting.application.ChatGptClient.GptAnswerResponse.Choice.ChoiceMessage;
import briefing.chatting.application.dto.AnswerRequest;
import briefing.chatting.application.dto.MessageRequest;
import briefing.chatting.domain.Chatting;
import briefing.chatting.domain.GptModel;
import briefing.chatting.domain.Message;
import briefing.chatting.domain.MessageRole;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class ChatGptClient {

  private static final float TEMPERATURE = 1;

  private final RestTemplate restTemplate;

  @Value("${openai.url.chat}")
  private String chatUrl;
  @Value("${openai.token}")
  private String token;

  public Message requestAnswer(final Chatting chatting, final AnswerRequest request) {
    final HttpEntity<GptAnswerRequest> requestEntity = generateRequestEntity(request);

    final GptAnswerResponse response = restTemplate.exchange(chatUrl, HttpMethod.POST,
            requestEntity, GptAnswerResponse.class)
        .getBody();

    return new Message(chatting, response.getRole(), response.getContent());
  }

  private HttpEntity<GptAnswerRequest> generateRequestEntity(final AnswerRequest request) {
    final HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    headers.setBearerAuth(token);

    final GptAnswerRequest requestBody = GptAnswerRequest.from(request, TEMPERATURE);

    return new HttpEntity<>(requestBody, headers);
  }

  record GptAnswerRequest(GptModel model, float temperature, List<MessageRequest> messages) {

    public static GptAnswerRequest from(final AnswerRequest request, final float temperature) {
      return new GptAnswerRequest(
          request.model(),
          temperature,
          request.messages()
      );
    }
  }

  record GptAnswerResponse(
      List<Choice> choices
  ) {

    public MessageRole getRole() {
      return getAnswer().role();
    }

    public String getContent() {
      return getAnswer().content();
    }

    private ChoiceMessage getAnswer() {
      return choices.get(0).message();
    }

    record Choice(
        ChoiceMessage message
    ) {

      record ChoiceMessage(
          MessageRole role,
          String content
      ) {

      }
    }
  }
}
