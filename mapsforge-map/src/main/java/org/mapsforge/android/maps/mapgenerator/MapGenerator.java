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
package org.mapsforge.android.maps.mapgenerator;

import org.mapsforge.core.GeoPoint;

import android.graphics.Bitmap;

/**
 * A MapGenerator provides map tiles either by downloading or rendering them.
 */
public interface MapGenerator {
	/**
	 * Called once at the end of the MapGenerator lifecycle.
	 */
	void cleanup();

	/**
	 * Called when a job needs to be executed.
	 * 
	 * @param mapGeneratorJob
	 *            the job that should be executed.
	 * @param bitmap
	 *            the bitmap for the generated map tile.
	 * @return true if the job was executed successfully, false otherwise.
	 */
	boolean executeJob(MapGeneratorJob mapGeneratorJob, Bitmap bitmap);

	/**
	 * @return the start point of this MapGenerator (may be null).
	 */
	GeoPoint getStartPoint();

	/**
	 * @return the start zoom level of this MapGenerator (may be null).
	 */
	Byte getStartZoomLevel();

	/**
	 * @return the maximum zoom level that this MapGenerator supports.
	 */
	byte getZoomLevelMax();

	/**
	 * @return true if this MapGenerator requires an Internet connection, false otherwise.
	 */
	boolean requiresInternetConnection();
}
