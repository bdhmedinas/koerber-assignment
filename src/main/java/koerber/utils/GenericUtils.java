/**
 * 
 */
package koerber.utils;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

/**
 * Provides project wide utilities
 * 
 * @author Bruno Medinas
 *
 */
public class GenericUtils {

	/**
	 * Maps a given collection of type T to a List of type M
	 * 
	 * @param <M>
	 * @param <T>
	 * @param col   - Collection that required mapping
	 * @param toMap - class type to map to
	 * @return List of mapped values.
	 */
	public static <M, T> List<M> mapAll(final Collection<T> col, Class<M> toMap) {
		ModelMapper modelMapper = new ModelMapper();
		return col.stream().map(entity -> modelMapper.map(entity, toMap)).collect(Collectors.toList());
	}

}
