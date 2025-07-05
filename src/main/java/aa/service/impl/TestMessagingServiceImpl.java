package aa.service.impl;

import aa.dto.TestDTO;
import aa.service.TestMessagingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class TestMessagingServiceImpl implements TestMessagingService {

    private final RabbitTemplate rabbitTemplate;

    @Override
    public void sendMessage(final TestDTO testDTO) {
        testDTO.setRandomId(UUID.randomUUID().toString());
        log.info("sendMessage----->{}", testDTO);
        MessageConverter converter = rabbitTemplate.getMessageConverter();
        MessageProperties props = new MessageProperties();
        props.setHeader("X_ORDER_SOURCE", "WEB");
        Message message = converter.toMessage(testDTO, props);

//        rabbitTemplate.convertAndSend("tacocloud.order.queue", message);

        rabbitTemplate.convertAndSend("tacocloud.order.queue", message, message1 -> {
            MessageProperties props1 = message1.getMessageProperties();
            props1.setHeader("X_ORDER_SOURCE", "WEB");
            return message1;
        });
    }
}
