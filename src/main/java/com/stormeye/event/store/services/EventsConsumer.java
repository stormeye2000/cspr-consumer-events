package com.stormeye.event.store.services;

import com.casper.sdk.model.event.EventTarget;
import com.casper.sdk.model.event.EventType;
import com.casper.sdk.service.EventService;
import com.stormeye.event.store.exceptions.EventConsumerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author ian@meywood.com
 */
@Service
public class EventsConsumer {

    private final Logger logger = LoggerFactory.getLogger(EventsConsumer.class);

    private  EventService eventService;

    public EventsConsumer() {
       /* try {
           // eventService = EventService.usingPeer(new URI("http://localhost"));
        } catch (URISyntaxException e) {
            throw new EventConsumerException(e);
        }*/
    }

    @KafkaListener(topics = {"main", "deploys", "sigs"})
    public void consumeWithHeaders(@Header(KafkaHeaders.RECEIVED_TOPIC) String topic, @Payload String event) {
        try {
            ///eventService.consumeEvents(EventType.valueOf(topic.toUpperCase()), EventTarget.POJO, event);
            // TODO eventAuditService.save(event);
            logger.debug("Successfully processed topic [{}]: event {}", topic, event);

        } catch (Exception e) {
            logger.error("Error in topic {} event {}", topic, event, e);
        }
    }
}
