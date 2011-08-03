package com.vimukti.accounter.core;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.vimukti.accounter.utils.HibernateUtil;

public class NumberUtils {

	public static String getNextTransactionNumber(int transactionType) {

		String prevNumber = getPreviousTransactionNumber(transactionType);

		return getStringwithIncreamentedDigit(prevNumber);
	}

	public static String getPreviousTransactionNumber(int transactionType) {

		Query query = HibernateUtil
				.getCurrentSession()
				.getNamedQuery(
						"getTransactionNumber.by.type")
				.setParameter("transactionType", transactionType);

		List list = query.list();
		if ((list.size() == 0)) {
			return "0";
		}

		for (int i = list.size() - 1; i >= 0; i--) {
			String num = (String) list.get(i);
			if (num.replaceAll("[\\D]", "").length() > 0) {
				return num;
			}
			// else {
			// if (maxCount != 0) {
			// maxCount = maxCount - 1;
			// return getPreviousTransactionNumber(transactionType,
			// maxCount);
			// }
			// }
		}

		return "0";
	}

	
	public static String getNextVoucherNumber() {

		Session session = HibernateUtil.getCurrentSession();
		Query query = session
				.getNamedQuery("get.Entry");
		List list1 = query.list();

		if (list1.size() <= 0) {

			return getNextTransactionNumber(Transaction.TYPE_JOURNAL_ENTRY);
		}

		query = session
				.getNamedQuery("getEntry.byId.andMax");
		List list = query.list();

		if (list != null) {
			return getStringwithIncreamentedDigit(((String) list.get(0)));
		} else
			return "1";

	}

	public static String getNextFixedAssetNumber() {
		String prevNumber = getPreviousFixedAssetNumber();
		return getStringwithIncreamentedDigit(prevNumber);

	}

	private static String getPreviousFixedAssetNumber() {
		Session session = HibernateUtil.getCurrentSession();
		Query query = session
				.getNamedQuery("getassestNumber.from.FixedAsset");
		List<?> list = query.list();
		if (list.size() == 0) {
			return "0";
		}
		for (int i = list.size() - 1; i >= 0; i--) {
			String number = (String) list.get(i);
			if (number.replaceAll("//D", "").length() > 0) {
				return number;
			}

		}
		return null;
	}
	
	public static String getNextCustomerNumber() {

		String prevNumber = getPreviousCustomerNumber();

		return getStringwithIncreamentedDigitForCustomer(prevNumber);
	}
	
	public static String getPreviousCustomerNumber() {

		Query query = HibernateUtil
				.getCurrentSession()
				.getNamedQuery(
						"getCustomerNumber.byId.andOrder");

		List list = query.list();
		if ((list.size() == 0)) {
			return "0";
		}

		for (int i = list.size() - 1; i >= 0; i--) {
			String num = (String) list.get(i);
			if (num.replaceAll("[\\D]", "").length() > 0) {
				return num;
			} else
				return num+"0";
		}

		return "0";
	}

	public static String getStringwithIncreamentedDigit(String prevNumber) {

		String incredNumber = "";
		if (prevNumber != null) {
			for (int i = prevNumber.length() - 1; i >= 0; i--) {
				char ch = prevNumber.charAt(i);

				if (incredNumber.length() > 0 && !Character.isDigit(ch)) {
					break;
				} else if (Character.isDigit(ch)) {
					incredNumber = ch + incredNumber;
				}
			}

		}
		if (incredNumber.length() > 0) {
			// incredNumber = new
			// StringBuffer(incredNumber).reverse().toString();
			prevNumber = prevNumber.replace(incredNumber, ""
					+ (Long.parseLong(incredNumber) + 1));
		}
		return prevNumber;

	}
	
	public static String getStringwithIncreamentedDigitForCustomer(String prevNumber) {

		String incredNumber = "";
		if (prevNumber != null) {
			for (int i = prevNumber.length() - 1; i >= 0; i--) {
				char ch = prevNumber.charAt(i);

				if (incredNumber.length() > 0 && !Character.isDigit(ch)) {
					break;
				} else if (Character.isDigit(ch)) {
					incredNumber = ch + incredNumber;
				}
			}

		}
		if (incredNumber.length() > 0) {
			
				prevNumber = prevNumber.replace(incredNumber, ""
					+ (new BigInteger(incredNumber).add(BigInteger.valueOf(1))));
				
		}
		return prevNumber;

	}
}
