package org.jonathan.reactive.message.demo;

import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.reactivestreams.Publisher;

/**
 * @description:
 * @author: Jonathan.Hu
 * @since:
 * @create: 2021-04-18 11:51
 **/
public interface DefaultService {
    @Outgoing("my-channel")
    Publisher<Integer> data();
}
