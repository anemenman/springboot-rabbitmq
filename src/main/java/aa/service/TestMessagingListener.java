package aa.service;

import aa.dto.TestDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TestMessagingListener {

    @RabbitListener(queues = "tacocloud.order.queue")
    public void receiveOrder(TestDTO testDTO) {
        log.info("Received: {}", testDTO);
    }
}
