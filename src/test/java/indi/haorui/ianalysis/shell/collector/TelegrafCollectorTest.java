package indi.haorui.ianalysis.shell.collector;

import org.junit.jupiter.api.Test;

import java.net.SocketException;

/**
 * Created by Yang Hao.rui on 2023/8/28
 */
class TelegrafCollectorTest {

    @Test
    void test() throws SocketException {
        TelegrafCollector telegrafCollector = new TelegrafCollector();
        telegrafCollector.act();
    }


}