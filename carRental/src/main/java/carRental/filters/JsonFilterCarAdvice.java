package carRental.filters;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import carRental.DTO.CarDto;
import carRental.comparators.DynamicCarComparator;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class JsonFilterCarAdvice implements ResponseBodyAdvice<List<CarDto>> {
	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		List<Annotation> annotations = Arrays.asList(returnType.getMethodAnnotations());
		return annotations.stream().anyMatch(annotation -> annotation.annotationType().equals(JsonFilter.class));
	}

	@Override
	public List<CarDto> beforeBodyWrite(List<CarDto> body, MethodParameter returnType, MediaType selectedContentType,
			Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
			ServerHttpResponse response) {
		List<CarDto> values = body;

		// Identify keys we are interested in.
		JsonFilter annotation = returnType.getMethodAnnotation(JsonFilter.class);
		List<String> possibleFilters = Arrays.asList(annotation.keys());

		HttpServletRequest servletRequest = ((ServletServerHttpRequest) request).getServletRequest();

		List<CarDto> result = values.stream().filter(map -> {
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
		// colectioneaza in lista, as putea
		// compara ceva ?
		// result.ORDER whatever

		// Chestie importanta//
		// parcurg din nou parametrii primiti in URL si verific pt proprii mei
		// parametrii

		Enumeration<String> parameterNames = servletRequest.getParameterNames();
		while (parameterNames.hasMoreElements()) {
			String parameterName = parameterNames.nextElement();
			if (parameterName.equals("pricePerDay-ord")) {
				DynamicCarComparator d0 = new DynamicCarComparator(0);
				String parameterValue = servletRequest.getParameter(parameterName);
				if (parameterValue.equals("asc")) {
					Collections.sort(result, d0);
				}
				if (parameterValue.equals("desc")) {
					d0.compareBy(1);
					Collections.sort(result, d0);
				}
			}

			if (parameterName.equals("pricePerDay-max")) {
				DynamicCarComparator d0 = new DynamicCarComparator(0);
				String parameterValue = servletRequest.getParameter(parameterName);

				for (Iterator<CarDto> iterator = result.iterator(); iterator.hasNext();) {
					CarDto car = iterator.next();
					if (car.pricePerDay > Integer.parseInt(parameterValue)) {
						iterator.remove();
					}
				}
			}

			if (parameterName.equals("pricePerDay-min")) {
				String parameterValue = servletRequest.getParameter(parameterName);

				for (Iterator<CarDto> iterator = result.iterator(); iterator.hasNext();) {
					CarDto car = iterator.next();
					if (car.pricePerDay < Integer.parseInt(parameterValue)) {
						iterator.remove();
					}
				}
			}

		}

		// paginare
		// OBS
		// la GET ar fi optim ca parametrii de ordonare sa fie inaintea
		// paginarii
		// IE: sa se faca paginarea dupa ordonare -- De asta parcurg din nou parametrii

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

					// toIndex exclusive
					return result.subList(fromIndex, Math.min(fromIndex + pageSize, result.size()));
					// face returnarea aici, deci orice alti parametrii sugerez a fi prelucrati inaintea paginarii
				}
			}

		}
		return result;
	}
}