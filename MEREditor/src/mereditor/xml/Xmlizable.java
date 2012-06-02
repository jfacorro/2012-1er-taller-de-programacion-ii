package mereditor.xml;


import org.w3c.dom.Element;

public interface Xmlizable {
	public Element toXml();
	public void fromXml(Element elemento, ParserXml parser) throws Exception;
}
