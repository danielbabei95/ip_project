package com.hotelsamdrooms.filters;


import com.hotelsamdrooms.DTO.HotelDto;
import com.hotelsamdrooms.comparators.DynamicHotelComparator;
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
import java.util.*;
import java.util.stream.Collectors;

@ControllerAdvice
public class JsonFilterHotels implements ResponseBodyAdvice<List<HotelDto>> {
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        List<Annotation> annotations = Arrays.asList(returnType.getMethodAnnotations());
        return annotations.stream().anyMatch(annotation -> annotation.annotationType().equals(JsonFilter.class));
    }

    @Override
    public List<HotelDto> beforeBodyWrite(List<HotelDto> body, MethodParameter returnType, MediaType selectedContentType,
                                        Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
                                        ServerHttpResponse response) {
        List<HotelDto> values = body;

        // Identify keys we are interested in.
        JsonFilter annotation = returnType.getMethodAnnotation(JsonFilter.class);
        List<String> possibleFilters = Arrays.asList(annotation.keys());

        HttpServletRequest servletRequest = ((ServletServerHttpRequest) request).getServletRequest();

        List<HotelDto> result = values.stream().filter(map -> {
            boolean match = true;
            Enumeration<String> parameterNames = servletRequest.getParameterNames();
            while (parameterNames.hasMoreElements()) {
                String parameterName = parameterNames.nextElement();
                if (possibleFilters.contains(parameterName)) {
                    String parameterValue = servletRequest.getParameter(parameterName);
                    Object valueFromMap = null;// map.get(parameterName);
                    try {
                        Field field = map.getClass().getField(parameterName);
                        field.setAccessible(true);
                        valueFromMap = field.get(map);
                        // System.out.println(field.get(map));
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
					/*
					 * Method m =
					 * map.getClass().getMethod("get"+parameterName.substring(0,
					 * 1).toUpperCase() +parameterName.substring(1));
					 * valueFromMap =m.invoke(map); }
					 */
                    match = (valueFromMap != null)
                            && valueFromMap.toString().toLowerCase().equals(parameterValue.toString().toLowerCase());
                }
            }
            return match;
        }).collect(Collectors.toList());

        Enumeration<String> parameterNames = servletRequest.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String parameterName = parameterNames.nextElement();
            if (parameterName.equals("category-ord")) {
                DynamicHotelComparator d0 = new DynamicHotelComparator(0);
                String parameterValue = servletRequest.getParameter(parameterName);
                if (parameterValue.equals("asc")) {
                    Collections.sort(result, d0);
                }
                if (parameterValue.equals("desc")) {
                    d0.compareBy(1);
                    Collections.sort(result, d0);
                }
            }

            if (parameterName.equals("category-max")) {
                DynamicHotelComparator d0 = new DynamicHotelComparator(0);
                String parameterValue = servletRequest.getParameter(parameterName);

                for (Iterator<HotelDto> iterator = result.iterator(); iterator.hasNext();) {
                    HotelDto hotel = iterator.next();
                    if (hotel.category > Float.parseFloat(parameterValue)) {
                        iterator.remove();
                    }
                }
            }

            if (parameterName.equals("category-min")) {
                String parameterValue = servletRequest.getParameter(parameterName);

                for (Iterator<HotelDto> iterator = result.iterator(); iterator.hasNext();) {
                    HotelDto hotel = iterator.next();
                    if (hotel.category < Float.parseFloat(parameterValue)) {
                        iterator.remove();
                    }
                }
            }

        }

        parameterNames = servletRequest.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String parameterName = parameterNames.nextElement();
            if (parameterName.equals("page")) {
                if (servletRequest.getParameter("pageSize") != null) {

                    int page = Integer.parseInt(servletRequest.getParameter("page"));
                    int pageSize = Integer.parseInt(servletRequest.getParameter("pageSize"));

                    if (pageSize <= 0 || page <= 0) {
                        throw new IllegalArgumentException("invalid page size: " + pageSize);
                    }

                    int fromIndex = (page - 1) * pageSize;
                    if (result == null || result.size() < fromIndex) {
                        return Collections.emptyList();
                    }

                    return result.subList(fromIndex, Math.min(fromIndex + pageSize, result.size()));
                }
            }

        }
        return result;
    }
}
