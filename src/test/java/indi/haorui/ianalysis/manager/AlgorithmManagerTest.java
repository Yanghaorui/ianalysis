package indi.haorui.ianalysis.manager;

import indi.haorui.ianalysis.model.cpu.CPUManager;
import indi.haorui.ianalysis.shell.collector.TelegrafCollector;
import org.junit.jupiter.api.Test;

import java.net.SocketException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Yang Hao.rui on 2023/9/14
 */
class AlgorithmManagerTest {

    @Test
    void algorithmTest() throws SocketException, InterruptedException {
        TelegrafCollector telegrafCollector = new TelegrafCollector();
        telegrafCollector.init();
        new ConverterManager().init();
        new CPUManager().init();
       Thread.sleep(1_000_000);
    }

    @Test
    void subscribe() {
    }

    @Test
    void match() {
    }
}