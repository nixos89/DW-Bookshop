package com.nikolas.master_thesis.examples.osobe;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;

public final class Osoba {

	private final String ime;
	private final String prezime;
	private final Pol pol;
	private final LocalDate datumRodjenja;
	private final String mestoRodjenja;
	private final String mestoStanovanja;
	private final int primanja;
	private final List<Osoba> deca;

	public Osoba(String ime, String prezime, Pol pol, LocalDate datumRodjenja, String mestoRodjenja, String mestoStanovanja, int primanja, Osoba... deca) {
		if (ime == null) {
			throw new IllegalArgumentException();
		}
		this.ime = ime;
		if (prezime == null) {
			throw new IllegalArgumentException();
		}
		this.prezime = prezime;
		if (pol == null) {
			throw new IllegalArgumentException();
		}
		this.pol = pol;
		if (datumRodjenja == null) {
			throw new IllegalArgumentException();
		}
		this.datumRodjenja = datumRodjenja;
		if (mestoRodjenja == null) {
			throw new IllegalArgumentException();
		}
		this.mestoRodjenja = mestoRodjenja;
		if (mestoStanovanja == null) {
			throw new IllegalArgumentException();
		}
		this.mestoStanovanja = mestoStanovanja;
		this.primanja = primanja;
		if (deca == null) {
			throw new IllegalArgumentException();
		}
		List<Osoba> d = new ArrayList<>();
		for (Osoba dete : deca) {
			d.add(dete);
		}
		this.deca = Collections.unmodifiableList(d);
	}

	public String getIme() {
		return ime;
	}
	
	public String getPrezime() {
		return prezime;
	}
	
	public Pol getPol() {
		return pol;
	}

	public LocalDate getDatumRodjenja() {
		return datumRodjenja;
	}
	
	public String getMestoRodjenja() {
		return mestoRodjenja;
	}

	public String getMestoStanovanja() {
		return mestoStanovanja;
	}

	public int getPrimanja() {
		return primanja;
	}

	public List<Osoba> getDeca() {
		return deca;
	}

	public int getBrDece() {
		return getBrDece(null);
	}

	public int getBrDece(Pol pol) {
		if (pol == null) {
			return deca.size();
		}
		return (int) deca.stream()
			.filter(o -> o.getPol() == pol)
			.count();
	}

	@Override
	public String toString() {
		return String.format("%08X ", System.identityHashCode(this)) + ime + " " + prezime;
	}

	public static String toString(Osoba osoba) {
		StringJoiner joiner = new StringJoiner("|");
		joiner.add(osoba.ime);
		joiner.add(osoba.prezime);
		joiner.add(osoba.pol.toString());
		joiner.add(osoba.datumRodjenja.toString());
		joiner.add(osoba.mestoRodjenja);
		joiner.add(osoba.mestoStanovanja);
		joiner.add(String.valueOf(osoba.primanja));
		joiner.add(String.valueOf(osoba.deca.size()));
		for (Osoba dete : osoba.deca) {
			joiner.add(dete.toString());
		}
		return joiner.toString();
	}

	public static Osoba fromString(String string) {
		String[] podaci = string.split("\\|");
		if (podaci.length % 8 != 0) {
			throw new IllegalArgumentException();
		}
		Osoba osoba = fromString(podaci[0], podaci[1], podaci[2], podaci[3], podaci[4], podaci[5], podaci[6]);
		try {
			int brDece = Integer.parseInt(podaci[7]);
			Osoba[] deca = new Osoba[brDece];
			for (int i = 0; i < brDece; i++) {
				deca[i] = fromString(podaci[8 * i + 0], podaci[8 * i + 1], podaci[8 * i + 2], podaci[8 * i + 3], podaci[8 * i + 4], podaci[8 * i + 5], podaci[8 * i + 6]);
			}
			return new Osoba(osoba.ime, osoba.prezime, osoba.pol, osoba.datumRodjenja, osoba.mestoRodjenja, osoba.mestoStanovanja, osoba.primanja, deca);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(e);
		}
	}

	private static Osoba fromString(String... podaci) {
		try {
			return new Osoba(podaci[0], podaci[1], Pol.valueOf(podaci[2]), LocalDate.parse(podaci[3]), podaci[4], podaci[5], Integer.parseInt(podaci[6]));
		} catch (NullPointerException | DateTimeParseException | NumberFormatException | IndexOutOfBoundsException e) {
			throw new IllegalArgumentException(e);
		}
	}
}
