package indi.haorui.ianalysis.alarm;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Yang Hao.rui on 2023/9/14
 */
class AlarmManagerTest {

    @Test
    void testJep(){
        String expression = "cpu-usage.x < result.x.b";
        Expression compile = AviatorEvaluator.compile(expression);
        Map<String, Object> map = new HashMap<>();
        map.put("cpu-usage.x", 0.1);
        map.put("result.x.b", 0.8);
        Assertions.assertTrue((boolean) compile.execute(map));
    }

}