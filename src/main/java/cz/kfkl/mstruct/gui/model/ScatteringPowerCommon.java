package cz.kfkl.mstruct.gui.model;

import cz.kfkl.mstruct.gui.model.utils.XmlLinkedModelElement;
import cz.kfkl.mstruct.gui.utils.JvStringUtils;
import cz.kfkl.mstruct.gui.xml.annotation.XmlAttributeProperty;
import cz.kfkl.mstruct.gui.xml.annotation.XmlMappedSubclasses;
import cz.kfkl.mstruct.gui.xml.annotation.XmlUniqueElement;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.paint.Color;

@XmlMappedSubclasses({ ScatteringPowerAtomElement.class, ScatteringPowerSphereElement.class })
public abstract class ScatteringPowerCommon extends XmlLinkedModelElement implements ParamContainer {

	@XmlAttributeProperty("Name")
	public StringProperty nameProperty = new SimpleStringProperty();

	@XmlUniqueElement("RGBColour")
	public SingleValueUniqueElement colourElement = new SingleValueUniqueElement("1 0 0");

	@Override
	public String formatParamContainerName() {
		return "Scattering Power " + getType() + ": " + getName();
	}

	public String getName() {
		return nameProperty.get();
	}

	public void setName(String name) {
		this.nameProperty.set(name);
	}

	public String getColour() {
		return colourElement.valueProperty.get();
	}

	public void setColour(String colour) {
		this.colourElement.valueProperty.set(colour);
	}

	public abstract String getType();

	public Color getColor() {
		String rgbStr = colourElement.valueProperty.get();
		String[] split = rgbStr.split(" ");

		double red = parseSplit(split, 0);
		double green = parseSplit(split, 1);
		double blue = parseSplit(split, 2);

		return Color.color(red, green, blue);
	}

	private double parseSplit(String[] split, int index) {
		Double val = null;
		if (split.length > index) {
			String strVal = split[index];
			val = JvStringUtils.parseDoubleSilently(strVal);
		}

		return val == null ? 0 : val;
	}

	public void setColor(Color color) {
		this.colourElement.valueProperty.set(JvStringUtils.join(" ", JvStringUtils.toStringNoDotZero(color.getRed()),
				JvStringUtils.toStringNoDotZero(color.getGreen()), JvStringUtils.toStringNoDotZero(color.getBlue())));
	}

}
