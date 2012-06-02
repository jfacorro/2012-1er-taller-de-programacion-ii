package mereditor.xml;

public class Constants {
	public static final String ID_ATTR = "id";
	public static final String TIPO_ATTR = "tipo";
	public static final String ESTADO_ATTR = "estado";
	public static final String IDREF_ATTR = "idref";
	public static final String CARDINALIDAD_MIN_ATTR = "min";
	public static final String CARDINALIDAD_MAX_ATTR = "max";
	public static final String NOMBRE_TAG = "Nombre";

	public static final String ENTIDAD_TAG = "Entidad";
	public static final String ATRIBUTO_TAG = "Atributo";
	public static final String RELACION_TAG = "Relacion";	
	public static final String DIAGRAMA_TAG = "Diagrama";
	public static final String JERARQUIA_TAG = "Jerarquia";
	public static final String VALIDACION_TAG = "Validacion";

	/**********************************************************************************/
	
	public static final String DIAGRAMA_COMPONENTES_QUERY = "./Componentes/Componente";
	public static final String DIAGRAMA_DIAGRAMAS_QUERY = "./Diagramas/Diagrama";
	
	public static final String ATRIBUTOS_QUERY = "./Atributos/Atributo";

	public static final String IDENTIFICADORES_INTERNOS_QUERY = "./IdentificadoresInternos/RefAtributo";
	public static final String IDENTIFICADORES_EXTERNOS_QUERY = "./IdentificadoresExternos/RefEntidad";
	
	public static final String ID_QUERY = "//*[@id='%s']";
	public static final String FORMULA_QUERY = "./Formula";
	public static final String ORIGINAL_QUERY = "./Origen/RefAtributo";
	public static final String GENERICA_QUERY = "./Generica/RefEntidad";
	public static final String DERIVADAS_QUERY = "./Derivadas/RefEntidad";
	public static final String PARTICIPANTES_QUERY = "./Participantes/Participante";
	public static final String ENTIDAD_REF_QUERY = "./RefEntidad";
	public static final String ROL_QUERY = "./Rol";
	public static final String CARDINALIDAD_QUERY = "./Cardinalidad";
	public static final String VALIDACION_QUERY = "./Validacion";
	public static final String OBSERVACIONES_QUERY = "./Observaciones";
}
