package com.dmcc.dmcc_crm.util;

import com.dmcc.dmcc_crm.bean.User;

import java.util.Comparator;


/**
 * 
 * @author xiaanming
 * 
 */
public class PinyinComparator implements Comparator<User> {

	@Override
	public int compare(User o1, User o2) {
		if (o1.getFirstLetter().equals("@") || o2.getFirstLetter().equals("#")) {
			return -1;
		} else if (o1.getFirstLetter().equals("#")
				|| o2.getFirstLetter().equals("@")) {
			return 1;
		} else {
			return o1.getFirstLetter().compareTo(o2.getFirstLetter());
		}
	}

}
