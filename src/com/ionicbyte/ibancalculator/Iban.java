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

import java.math.BigInteger;

public class Iban {

	static String getIban(String countryCode, String entity, String office,
			String dc, String accountNumber) {
		String dcIban = ccIban(countryCode, entity, office, dc, accountNumber);
		String iban = countryCode + dcIban + " " + entity + " " + office + " "
				+ dc + " " + accountNumber;
		return iban;
	}

	static String ccIban(String countryCode, String entity, String office,
			String dc, String accountNumber) {

		String preIban = entity + office + dc + accountNumber
				+ valueIBAN(countryCode.charAt(0))
				+ valueIBAN(countryCode.charAt(1)) + "00";
		BigInteger ccc = new BigInteger(preIban);
		ccc = ccc.mod(new BigInteger("97"));
		int dcIb = ccc.intValue();
		dcIb = 98 - dcIb;
		return leftZero(Integer.toString(dcIb), 2);
	}

	static String leftZero(String str, int lon) {
		String zero = "";
		if (str.length() < lon) {
			for (int i = 0; i < (lon - str.length()); i++) {
				zero = zero + '0';
			}
			str = zero + str;
		}

		return str;
	}

	static String valueIBAN(char letter) {
		String weight = "";
		letter = Character.toUpperCase(letter);
		
		// Not the most elegant way to do it, but it will do the trick.
		switch (letter) {
		case 'A':
			weight = "10";
			break;
		case 'B':
			weight = "11";
			break;
		case 'C':
			weight = "12";
			break;
		case 'D':
			weight = "13";
			break;
		case 'E':
			weight = "14";
			break;
		case 'F':
			weight = "15";
			break;
		case 'G':
			weight = "16";
			break;
		case 'H':
			weight = "17";
			break;
		case 'I':
			weight = "18";
			break;
		case 'J':
			weight = "19";
			break;
		case 'K':
			weight = "20";
			break;
		case 'L':
			weight = "21";
			break;
		case 'M':
			weight = "22";
			break;
		case 'N':
			weight = "23";
			break;
		case 'O':
			weight = "24";
			break;
		case 'P':
			weight = "25";
			break;
		case 'Q':
			weight = "26";
			break;
		case 'R':
			weight = "27";
			break;
		case 'S':
			weight = "28";
			break;
		case 'T':
			weight = "29";
			break;
		case 'U':
			weight = "30";
			break;
		case 'V':
			weight = "31";
			break;
		case 'W':
			weight = "32";
			break;
		case 'X':
			weight = "33";
			break;
		case 'Y':
			weight = "34";
			break;
		case 'Z':
			weight = "35";
			break;
		}
		return weight;
	}

}
