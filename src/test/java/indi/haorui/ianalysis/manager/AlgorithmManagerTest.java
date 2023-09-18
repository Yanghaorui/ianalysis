package indi.haorui.ianalysis.manager;

import indi.haorui.ianalysis.algorithm.AlgorithmManager;
import indi.haorui.ianalysis.algorithm.AverageAlgorithm;
import indi.haorui.ianalysis.model.cpu.CPUManager;
import indi.haorui.ianalysis.shell.collector.TelegrafCollector;
import org.junit.jupiter.api.Test;

import java.net.SocketException;

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
        AverageAlgorithm averageAlgorithm = new AverageAlgorithm(10, null);
        AlgorithmManager algorithmManager = new AlgorithmManager("1st algorithm", "cpu0::c5697c4de0a4", averageAlgorithm);
        Thread.sleep(1_000_000);
    }

    @Test
    void subscribe() {
    }

    @Test
    void match() {
    }
}