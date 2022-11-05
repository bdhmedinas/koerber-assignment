/**
 * 
 */
package koerber.util;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Bruno Medinas
 *
 */
public final class GenericUtils {

	@Autowired
	private static ModelMapper modelMapper;

	public static <T, S> List<T> mapList(List<S> list, Class<T> targetClass) {
		return list.stream().map(element -> modelMapper.map(element, targetClass)).collect(Collectors.toList());
	}

}
