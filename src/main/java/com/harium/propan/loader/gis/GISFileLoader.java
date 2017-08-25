package com.harium.propan.loader.gis;

import java.io.File;
import java.io.IOException;

import com.harium.propan.gis.GISInfo;

public interface GISFileLoader {
	public GISInfo loadGISInfo(File file) throws IOException;
}
