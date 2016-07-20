package edu.isi.karma.services.entityExtraction;

import java.util.List;

public interface IExtractionService {
	public List<OutputExtraction> performExt(List<InputExtraction> input);
}
