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
package org.mapsforge.map.reader;

import java.util.List;

import org.mapsforge.core.Tag;

/**
 * Callback methods which can be triggered from the {@link MapDatabase}.
 */
public interface MapDatabaseCallback {
	/**
	 * Renders a single point of interest node (POI).
	 * 
	 * @param layer
	 *            the layer of the node.
	 * @param latitude
	 *            the latitude of the node.
	 * @param longitude
	 *            the longitude of the node.
	 * @param tags
	 *            the tags of the node.
	 */
	void renderPointOfInterest(byte layer, int latitude, int longitude, List<Tag> tags);

	/**
	 * Renders water background for the current tile.
	 */
	void renderWaterBackground();

	/**
	 * Renders a single way or area (closed way).
	 * 
	 * @param layer
	 *            the layer of the way.
	 * @param labelPosition
	 *            the position of the area label in the order longitude/latitude (may be null).
	 * @param tags
	 *            the tags of the way.
	 * @param wayNodes
	 *            the geographical coordinates of the way nodes in the order longitude/latitude.
	 */
	void renderWay(byte layer, float[] labelPosition, List<Tag> tags, float[][] wayNodes);
}
