package ch.unisg.ems.sales.util;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import ch.unisg.ems.sales.dto.CamundaMessageDto;
import ch.unisg.ems.sales.dto.MessageProcessDto;
import org.camunda.bpm.engine.variable.VariableMap;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@UtilityClass
public class VariablesUtil {

    public <T> Map<String, Object> toVariableMap(T object) throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        Map<String, Object> variables = new HashMap<>();
        BeanInfo info = Introspector.getBeanInfo(object.getClass());
        for (PropertyDescriptor pd : info.getPropertyDescriptors()) {
            Method reader = pd.getReadMethod();
            if (reader != null && !pd.getName().equals("class")) {
                Object value = reader.invoke(object);
                if(value != null) {
                    variables.put(pd.getName(), reader.invoke(object));
                }
            }
        }
        return  variables;
    }

    public <T> CamundaMessageDto buildCamundaMessageDto(String businessKey, Map<String, Object> variablesMap){
        return CamundaMessageDto.builder().correlationId(businessKey)
                .dto(MessageProcessDto.builder().requester((String) variablesMap.get("requester"))
                        .amount((Double) variablesMap.get("amount"))
                        .preApproved((Boolean) variablesMap.get("preApproved"))
                        .processed((Boolean) variablesMap.get("processed"))
                        .build()).build();
    }
}
