package airportmanagement;

import airportmanagement.DTO.FlightDto;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class JsonFilterAdvice implements ResponseBodyAdvice<List<FlightDto>> {
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        List<Annotation> annotations = Arrays.asList(returnType.getMethodAnnotations());
        return annotations.stream().anyMatch(annotation -> annotation.annotationType().equals(JsonFilter.class));
    }

    @Override
    public List<FlightDto> beforeBodyWrite(List<FlightDto> body, MethodParameter returnType, MediaType selectedContentType, Class<? extends
            HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        List<FlightDto> values =  body;

        // Identify keys we are interested in.
        JsonFilter annotation = returnType.getMethodAnnotation(JsonFilter.class);
        List<String> possibleFilters = Arrays.asList(annotation.keys());

        HttpServletRequest servletRequest = ((ServletServerHttpRequest) request).getServletRequest();

        List<FlightDto> result = values.stream().filter(map -> {
            boolean match = true;
            Enumeration<String> parameterNames = servletRequest.getParameterNames();
            while (parameterNames.hasMoreElements()) {
                String parameterName = parameterNames.nextElement();
                if(possibleFilters.contains(parameterName)) {
                    String parameterValue = servletRequest.getParameter(parameterName);
                    Object valueFromMap = null;//map.get(parameterName);
                    try {
                       Field field = map.getClass().getField(parameterName);
                        field.setAccessible(true);
                        valueFromMap = field.get(map);
//                        System.out.println(field.get(map));
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                 /*
                                Method m = map.getClass().getMethod("get"+parameterName.substring(0, 1).toUpperCase() +parameterName.substring(1));
                        valueFromMap =m.invoke(map);
                    }*/
                    match = (valueFromMap != null) && valueFromMap.toString().toLowerCase().equals(parameterValue.toString().toLowerCase());
                }
            }
            return match;
        }).collect(Collectors.toList());
        return result;
    }
}