/**
 * 
 */
package koerber.utils;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

/**
 * @author Bruno Medinas
 *
 */
public class GenericUtils {

	private static ModelMapper modelMapper;

	public static <M, T> List<M> mapAll(final Collection<T> col, Class<M> toMap) {
		return col.stream().map(entity -> modelMapper.map(entity, toMap)).collect(Collectors.toList());
	}

}
