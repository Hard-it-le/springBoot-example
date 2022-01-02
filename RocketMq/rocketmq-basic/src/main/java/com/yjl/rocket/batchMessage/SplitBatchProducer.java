package com.yjl.rocket.batchMessage;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @program: springboot-example
 * @author: yjl
 * @created: 2022/01/02
 * <p>
 * 如果批量消息很多，就拆封成每批4mb然后分次发送
 *
 * 批量消息的使用是有一定限制的，这些消息应该有相同的Topic，相同的waitStoreMsgOK。
 * 而且不能是延迟消息、
 */
public class SplitBatchProducer {

    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException, MQBrokerException {

        DefaultMQProducer defaultMQProducer = new DefaultMQProducer("");
        defaultMQProducer.start();

        String topic = "BatchTest";
        List<Message> messages = new ArrayList<>(100 * 1000);

        for (int i = 0; i < 100 * 1000; i++) {
            Message message = new Message(topic, "Tag", "OrderId" + i, ("hello world" + i).getBytes(StandardCharsets.UTF_8));
            messages.add(message);
        }

        ListSplitter listSplitter = new ListSplitter(messages);
        while (listSplitter.hasNext()) {
            List<Message> listItem = listSplitter.next();
            defaultMQProducer.send(listItem);
        }
        defaultMQProducer.shutdown();

    }
}


/**
 * 批量消息拆分规则
 */
class ListSplitter implements Iterator<List<Message>> {
    private int sizeLimit = 1000 * 1000;
    private final List<Message> messages;
    private int currIndex;

    public ListSplitter(List<Message> messages) {
        this.messages = messages;
    }

    @Override
    public boolean hasNext() {
        return currIndex < messages.size();
    }

    @Override
    public List<Message> next() {
        int nextIndex = currIndex;
        int totalSize = 0;
        for (; nextIndex < messages.size(); nextIndex++) {
            Message message = messages.get(nextIndex);
            int tmpSize = message.getTopic().length() + message.getBody().length;
            Map<String, String> properties = message.getProperties();
            for (Map.Entry<String, String> entry : properties.entrySet()) {
                tmpSize += entry.getKey().length() + entry.getValue().length();
            }
            //for log overhead
            tmpSize = tmpSize + 20;
            if (tmpSize > sizeLimit) {
                //it is unexpected that single message exceeds the sizeLimit
                //here just let it go, otherwise it will block the splitting process
                if (nextIndex - currIndex == 0) {
                    //if the next sublist has no element, add this one and then break, otherwise just break
                    nextIndex++;
                }
                break;
            }
            if (tmpSize + totalSize > sizeLimit) {
                break;
            } else {
                totalSize += tmpSize;
            }

        }
        List<Message> subList = messages.subList(currIndex, nextIndex);
        currIndex = nextIndex;
        return subList;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Not allowed to remove");
    }
}
