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
package org.mapsforge.applications.android.advancedmapviewer.filefilter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.mapsforge.android.maps.rendertheme.RenderThemeHandler;
import org.mapsforge.map.reader.header.FileOpenResult;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

/**
 * Accepts all valid render theme XML files.
 */
public final class ValidRenderTheme implements ValidFileFilter {
	private FileOpenResult fileOpenResult;

	@Override
	public boolean accept(File file) {
		InputStream inputStream = null;

		try {
			inputStream = new FileInputStream(file);
			RenderThemeHandler renderThemeHandler = new RenderThemeHandler();
			XMLReader xmlReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
			xmlReader.setContentHandler(renderThemeHandler);
			xmlReader.parse(new InputSource(inputStream));
			this.fileOpenResult = FileOpenResult.SUCCESS;
		} catch (ParserConfigurationException e) {
			this.fileOpenResult = new FileOpenResult(e.getMessage());
		} catch (SAXException e) {
			this.fileOpenResult = new FileOpenResult(e.getMessage());
		} catch (IOException e) {
			this.fileOpenResult = new FileOpenResult(e.getMessage());
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (IOException e) {
				this.fileOpenResult = new FileOpenResult(e.getMessage());
			}
		}

		return this.fileOpenResult.isSuccess();
	}

	@Override
	public FileOpenResult getFileOpenResult() {
		return this.fileOpenResult;
	}
}
