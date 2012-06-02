package mereditor.parser;

public class Constants {
	public static final String ID_ATTR = "id";
	public static final String TIPO_ATTR = "tipo";
	public static final String ESTADO_ATTR = "estado";
	public static final String IDREF_ATTR = "idref";
	public static final String NOMBRE_TAG = "Nombre";

	public static final String ENTIDAD_TAG = "Entidad";
	public static final String ENTIDAD_IDS_INTERNOS_TAG = "IdentificadoresInternos";
	public static final String ENTIDAD_IDS_EXTERNOS_TAG = "IdentificadoresExternos";
	public static final String ENTIDAD_REF_TAG = "RefEntidad";
	
	public static final String ATRIBUTOS_TAG = "Atributos";
	
	public static final String ATRIBUTO_TAG = "Atributo";
	public static final String ATRIBUTO_REF_TAG = "RefAtributo";
	
	public static final String RELACION_TAG = "Relacion";	
	public static final String RELACION_PARTICIPANTES_TAG = "Participantes";

	public static final String RELACION_ROL_TAG = "Rol";
	public static final String REF_PARTICIPANTE_TAG = "Participante";

	public static final String CARDINALIDAD_MIN_ATTR = "min";
	public static final String CARDINALIDAD_MAX_ATTR = "max";
	public static final String CARDINALIDAD_TAG = "Cardinalidad";
	
	public static final String DIAGRAMAS_TAG = "Diagramas";
	public static final String DIAGRAMA_TAG = "Diagramas";
	public static final String DIAGRAMA_REF_TAG = "Diagrama";
	public static final String DIAGRAMA_COMPONENTES_TAG = "Componentes";
	public static final String DIAGRAMA_COMPONENTES_REF_TAG = "RefComponente";

	public static final String JERARQUIA_TAG = "Jerarquia";
	public static final String JERARQUIA_GENERICA_TAG = "Generica";
	public static final String JERARQUIA_DERIVADAS_TAG = "Derivadas";
	public static final String DERIVADA_REF_TAG = "Entidad";
	
	public static final String VALIDACION_OBSERVACIONES_TAG = "Observaciones";
	
	public static final String DIAGRAMA_COMPONENTES_QUERY = "./Componentes/Componente";
	public static final String DIAGRAMA_DIAGRAMAS_QUERY = "./Diagramas/Diagrama";
	
	public static final String ATRIBUTOS_QUERY = "./Atributos/Atributo";

	public static final String IDENTIFICADORES_INTERNOS_QUERY = "./IdentificadoresInternos/RefAtributo";
	public static final String IDENTIFICADORES_EXTERNOS_QUERY = "./IdentificadoresExternos/RefEntidad";
	
}
