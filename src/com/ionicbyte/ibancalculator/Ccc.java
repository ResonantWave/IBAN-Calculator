/* IBAN Calculator
Copyright (C) 2015 NiXijav Nitrozede

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>. */

package com.ionicbyte.ibancalculator;

public class Ccc {

	public static boolean validateCCC(String ccc) {
		try {
			String bank = ccc.substring(0, 4);
			String office = ccc.substring(4, 8);
			String dc = ccc.substring(8, 10);
			String cccAccount = ccc.substring(10);

			String bankOffice = "00" + bank + office;

			if (!obtainDC(bankOffice).equalsIgnoreCase(dc.substring(0, 1))
					|| !obtainDC(cccAccount).equalsIgnoreCase(
							dc.substring(1, 2)))
				return false;
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public static String obtainDC(String value) {
		int[] values = new int[] { 1, 2, 4, 8, 5, 10, 9, 7, 3, 6 };
		int control = 0;
		for (int i = 0; i <= 9; i++)
			control += Integer.parseInt(String.valueOf(value.charAt(i)))
					* values[i];
		control = 11 - (control % 11);
		if (control == 11)
			control = 0;
		else if (control == 10)
			control = 1;
		return String.valueOf(control);

	}

}
