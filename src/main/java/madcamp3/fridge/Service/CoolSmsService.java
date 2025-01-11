package madcamp3.fridge.Service;

import lombok.extern.slf4j.Slf4j;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CoolSmsService {
    @Value("${coolsms.api.key}")
    private String apiKey;

    @Value("${coolsms.api.secret}")
    private String apiSecret;

    @Value("${coolsms.sender.number}")
    private String senderNumber;

    private DefaultMessageService messageService;

    public void init() {
        this.messageService = NurigoApp.INSTANCE.initialize(apiKey, apiSecret, "https://api.coolsms.co.kr");
    }

    public void sendHealthAlert(String recipientNumber, String messageContent) {
        if (messageService == null) {
            init();
        }

        Message message = new Message();
        message.setFrom(senderNumber);
        message.setTo(recipientNumber);
        message.setText(messageContent);

        try {
            SingleMessageSentResponse response = messageService.sendOne(new SingleMessageSendingRequest(message));
            log.info("Message sent successfully. Response: {}", response);
        } catch (Exception e) {
            log.error("Failed to send message: ", e);
            throw new RuntimeException("SMS 발송 실패", e);
        }
    }
}