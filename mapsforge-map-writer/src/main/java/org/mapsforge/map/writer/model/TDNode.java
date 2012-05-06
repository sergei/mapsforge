/*
 * Copyright 2010, 2011, 2012 mapsforge.org
 *
 * This program is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.mapsforge.map.writer.model;

import java.util.Arrays;

import org.mapsforge.map.writer.OSMTagMapping;
import org.mapsforge.map.writer.util.OSMUtils;
import org.openstreetmap.osmosis.core.domain.v0_6.Node;

/**
 * @author bross
 */
public class TDNode {

	// private static final Logger LOGGER = Logger.getLogger(TDNode.class.getName());

	private static final byte ZOOM_HOUSENUMBER = (byte) 18;
	// private static final byte ZOOM_NAME = (byte) 16;

	private final long id;
	private final int latitude;
	private final int longitude;

	private final short elevation; // NOPMD by bross on 25.12.11 12:55
	private final String houseNumber;
	private final byte layer;
	private final String name;
	private short[] tags; // NOPMD by bross on 25.12.11 12:55

	/**
	 * Constructs a new TDNode from a given osmosis node entity. Checks the validity of the entity.
	 * 
	 * @param node
	 *            the osmosis entity
	 * @param preferredLanguage
	 *            the preferred language or null if no preference
	 * @return a new TDNode
	 */
	public static TDNode fromNode(Node node, String preferredLanguage) {
		SpecialTagExtractionResult ster = OSMUtils.extractSpecialFields(node, preferredLanguage);
		short[] knownWayTags = OSMUtils.extractKnownPOITags(node); // NOPMD by bross on 25.12.11 12:55

		return new TDNode(node.getId(), GeoCoordinate.doubleToInt(node.getLatitude()), GeoCoordinate.doubleToInt(node
				.getLongitude()), ster.getElevation(), ster.getLayer(), ster.getHousenumber(), ster.getName(),
				knownWayTags);
	}

	/**
	 * @param id
	 *            the OSM id
	 * @param latitude
	 *            the latitude
	 * @param longitude
	 *            the longitude
	 * @param elevation
	 *            the elevation if existent
	 * @param layer
	 *            the layer if existent
	 * @param houseNumber
	 *            the house number if existent
	 * @param name
	 *            the name if existent
	 */
	public TDNode(long id, int latitude, int longitude, short elevation, byte layer, String houseNumber, // NOPMD
																											// by
																											// bross
																											// on
																											// 25.12.11
																											// 12:55
			String name) {
		this.id = id;
		this.latitude = latitude;
		this.longitude = longitude;
		this.elevation = elevation;
		this.houseNumber = houseNumber;
		this.layer = layer;
		this.name = name;
	}

	/**
	 * @param id
	 *            the OSM id
	 * @param latitude
	 *            the latitude
	 * @param longitude
	 *            the longitude
	 * @param elevation
	 *            the elevation if existent
	 * @param layer
	 *            the layer if existent
	 * @param houseNumber
	 *            the house number if existent
	 * @param name
	 *            the name if existent
	 * @param tags
	 *            the
	 */
	public TDNode(long id, int latitude, int longitude, short elevation, byte layer, String houseNumber, // NOPMD
																											// by
																											// bross
																											// on
																											// 25.12.11
																											// 12:55
			String name, short[] tags) { // NOPMD by bross on 25.12.11 12:58
		this.id = id;
		this.latitude = latitude;
		this.longitude = longitude;
		this.elevation = elevation;
		this.houseNumber = houseNumber;
		this.layer = layer;
		this.name = name;
		this.tags = tags;
	}

	/**
	 * @return true if the node represents a POI
	 */
	public boolean isPOI() {
		return this.houseNumber != null || this.elevation != 0 || this.tags.length > 0;
	}

	/**
	 * @return the zoom level on which the node appears first
	 */
	public byte getZoomAppear() {
		if (this.tags == null || this.tags.length == 0) {
			if (this.houseNumber != null) {
				return ZOOM_HOUSENUMBER;
			}
			return Byte.MAX_VALUE;
		}
		return OSMTagMapping.getInstance().getZoomAppearPOI(this.tags);
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return this.id;
	}

	/**
	 * @return the tags
	 */
	public short[] getTags() { // NOPMD by bross on 25.12.11 12:58
		return this.tags; // NOPMD by bross on 25.12.11 12:56
	}

	/**
	 * @param tags
	 *            the tags to set
	 */
	public void setTags(short[] tags) { // NOPMD by bross on 25.12.11 12:58
		this.tags = tags;
	}

	/**
	 * @return the latitude
	 */
	public int getLatitude() {
		return this.latitude;
	}

	/**
	 * @return the longitude
	 */
	public int getLongitude() {
		return this.longitude;
	}

	/**
	 * @return the elevation
	 */
	public short getElevation() { // NOPMD by bross on 25.12.11 12:58
		return this.elevation;
	}

	/**
	 * @return the houseNumber
	 */
	public String getHouseNumber() {
		return this.houseNumber;
	}

	/**
	 * @return the layer
	 */
	public byte getLayer() {
		return this.layer;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	@Override
	public int hashCode() {
		final int prime = 31; // NOPMD by bross on 25.12.11 12:56
		int result = 1;
		result = prime * result + (int) (this.id ^ (this.id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		TDNode other = (TDNode) obj;
		if (this.id != other.id) {
			return false;
		}
		return true;
	}

	@Override
	public final String toString() {
		return "TDNode [id=" + this.id + ", latitude=" + this.latitude + ", longitude=" + this.longitude + ", name="
				+ this.name + ", tags=" + Arrays.toString(this.tags) + "]";
	}

}
