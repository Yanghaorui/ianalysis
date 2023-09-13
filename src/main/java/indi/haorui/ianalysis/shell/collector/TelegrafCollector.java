package indi.haorui.ianalysis.shell.collector;

import cn.hutool.json.JSONUtil;
import indi.haorui.ianalysis.actor.ActorSystem;
import indi.haorui.ianalysis.actor.SchedulerActor;
import indi.haorui.ianalysis.enums.ActorStatus;
import indi.haorui.ianalysis.hub.Hub;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * Created by Yang Hao.rui on 2023/8/28
 */
@Repository
public class TelegrafCollector extends SchedulerActor {
    private static DatagramSocket socket;
    private static final byte[] BUF = new byte[1 << 10];

    @PostConstruct
    public void init() throws SocketException {
        socket = new DatagramSocket(8094);
        this.interval = 1_000;
        this.status.set(ActorStatus.RUNNABLE);
        ActorSystem.register(this);
    }


    @Override
    public void act() {
        DatagramPacket packet
                = new DatagramPacket(BUF, BUF.length);
        try {
            socket.receive(packet);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        byte[] data = packet.getData();
        String s = new String(data, 0, packet.getLength());
        Output bean = JSONUtil.toBean(s, Output.class);
        Hub.hub(bean);
    }

    @Override
    public void terminal() {
        super.terminal();
        socket.close();
    }


}
