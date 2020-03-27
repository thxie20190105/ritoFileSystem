package org.rito.filesystem.util;

import org.springframework.cglib.beans.BeanCopier;

/**
 * @author yangbo
 *
 */
public class BeanCopyUtil {

	private BeanCopyUtil() {
	}

	/**
	 * 只支持同名、同类型copy
	 * 
	 * @param source
	 * @param target
	 */
	public static void copy(Object source, Object target) {
		BeanCopier bc = BeanCopier.create(source.getClass(), target.getClass(), false);
		bc.copy(source, target, null);
	}
}
