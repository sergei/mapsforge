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
package org.mapsforge.android.maps.rendertheme.renderinstruction;

import java.io.IOException;
import java.util.List;

import org.mapsforge.android.maps.rendertheme.RenderCallback;
import org.mapsforge.android.maps.rendertheme.RenderThemeHandler;
import org.mapsforge.core.Tag;
import org.xml.sax.Attributes;

import android.graphics.Bitmap;

/**
 * Represents an icon on the map.
 */
public final class Symbol implements RenderInstruction {
	/**
	 * @param elementName
	 *            the name of the XML element.
	 * @param attributes
	 *            the attributes of the XML element.
	 * @return a new Symbol with the given rendering attributes.
	 * @throws IOException
	 *             if an I/O error occurs while reading a resource.
	 */
	public static Symbol create(String elementName, Attributes attributes) throws IOException {
		String src = null;

		for (int i = 0; i < attributes.getLength(); ++i) {
			String name = attributes.getLocalName(i);
			String value = attributes.getValue(i);

			if ("src".equals(name)) {
				src = value;
			} else {
				RenderThemeHandler.logUnknownAttribute(elementName, name, value, i);
			}
		}

		validate(elementName, src);
		return new Symbol(src);
	}

	private static void validate(String elementName, String src) {
		if (src == null) {
			throw new IllegalArgumentException("missing attribute src for element: " + elementName);
		}
	}

	private final Bitmap bitmap;

	private Symbol(String src) throws IOException {
		super();

		this.bitmap = BitmapUtils.createBitmap(src);
	}

	@Override
	public void destroy() {
		this.bitmap.recycle();
	}

	@Override
	public void renderNode(RenderCallback renderCallback, List<Tag> tags) {
		renderCallback.renderPointOfInterestSymbol(this.bitmap);
	}

	@Override
	public void renderWay(RenderCallback renderCallback, List<Tag> tags) {
		renderCallback.renderAreaSymbol(this.bitmap);
	}

	@Override
	public void scaleStrokeWidth(float scaleFactor) {
		// do nothing
	}

	@Override
	public void scaleTextSize(float scaleFactor) {
		// do nothing
	}
}
