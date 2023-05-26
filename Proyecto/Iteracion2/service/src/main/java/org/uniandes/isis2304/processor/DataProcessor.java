package org.uniandes.isis2304.processor;

import com.google.auto.service.AutoService;
import org.uniandes.isis2304.core.Data;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * The HeaderProcessor class is a Java annotation processor that generates getters, setters, an all-args constructor, and a
 * private field for every field in classes annotated with the {@link Data} annotation. It also generates
 * an interface called VOClassName that contains the getters for the annotated class fields.
 * <p>It is annotated with the {@link SupportedAnnotationTypes} annotation with the value "annotation.core.Header", indicating
 * that it supports the processing of classes with the Header annotation.</p>
 * <p>The HeaderProcessor extends the {@link AbstractProcessor} abstract class, which provides a skeletal implementation of
 * the Processor interface.</p>
 * <p>The class has several private static helper methods, including:</p>
 * <ul>
 * <li>{@code getDefaultValue} - returns the default value for a given primitive data type</li>
 * <li>{@code generateConstructor} - generates the code for an all-args constructor</li>
 * <li>{@code generateEtters} - generates the code for getters and setters</li>
 * <li>{@code generatePrivateFields} - generates the code for private fields</li>
 * <li>{@code convert} - converts an Element to a Class object</li>
 * </ul>
 * <p>The class overrides the {@code process} method of the AbstractProcessor class, which is called by the annotation processing
 * tool to process the annotations. The {@code process} method calls the {@code generate} method for each Element in the set
 * of elements annotated with {@code Header}.</p>
 * <p>The {@code generate} method performs the following steps:</p>
 * <ol>
 * <li>Converts the Element to a Class object</li>
 * <li>Gets the declared fields of the Class object</li>
 * <li>Generates the private field code using the {@code generatePrivateFields} method</li>
 * <li>Generates the constructor code using the {@code generateConstructor} method</li>
 * <li>Generates the getters and setters code using the {@code generateEtters} method</li>
 * <li>Creates a Java source file using the {@code Filer} object</li>
 * <li>Writes the generated code to the Java source file</li>
 * </ol>
 * <p>The VOClassName interface contains the getters for the annotated class fields.</p>
 */
@AutoService(Processor.class)
@SupportedAnnotationTypes("org.uniandes.isis2304.core.Data")
@SupportedSourceVersion(SourceVersion.RELEASE_19)
public class DataProcessor extends AbstractProcessor {

    private Messager messager;
    private Filer filer;

    /**
     * Returns the default value for the given Java class type as a String.
     *
     * @param type the Java class type to get the default value for.
     * @return a String representing the default value for the given Java class type.
     */
    private static String getDefaultValue(String type) {
        return switch (type) {
            case "boolean" -> "false";
            case "byte", "short", "int" -> "0";
            case "long" -> "0L";
            case "float" -> "0.0f";
            case "double" -> "0.0";
            case "char" -> "'\\u0000'";
            default -> "null";
        };
    }

    /**
     * Generates an all-args constructor and a no-args constructor for a given class name and its fields.
     *
     * @param className the name of the class to generate the constructors for
     * @param fields    an array of fields representing the properties of the class
     * @return a String containing the source code for the generated constructors
     * @throws NullPointerException if either className or fields are null
     */
    private static String buildConstructor(String className, VariableElement[] fields) {
        StringBuilder sb = new StringBuilder();
        sb.append("\t@Generated(\"HeaderProcessor[all-args constructor]\")\n\t").append("public ").append(className).append("(");

        sb.append(Arrays.stream(fields)
                        .map(field1 -> field1.asType().toString() + " " + field1.getSimpleName())
                        .collect(Collectors.joining(", "))).append(")");
        sb.append(" {");
        Arrays.stream(fields)
              .forEach(field -> sb.append("\n\t\tthis.%1$s = %1$s;".formatted(field.getSimpleName())));
        sb.append("\n\t}\n\n");

        sb.append("\t@Generated(\"HeaderProcessor[no-args constructor]\")\n\t").append("public ").append(className).append("() {");
        for (VariableElement field : fields)
            sb.append("\n\t\t").append(String.format("this.%s = %s;", field.getSimpleName(), getDefaultValue(field.asType().toString())));
        sb.append("\n\t}");

        return sb.toString();
    }

    /**
     * Generates a customized toString() method for a given class with the specified fields.
     *
     * @param className the name of the class for which the toString() method is generated
     * @param fields    an array of fields for the given class
     * @return a String representation of the customized toString() method for the given class
     * @throws NullPointerException if either className or fields is null
     */
    private static String buildToString(String className, VariableElement[] fields) {
        StringBuilder sb = new StringBuilder();
        sb.append("""
                      \t@Override public String toString() {
                        \t\treturn "%s["+""".formatted(className));
        StringJoiner sj = new StringJoiner(" + \", \" + ");
        for (VariableElement field : fields) {
            String fName = field.getSimpleName().toString();
            String fValue = switch (field.asType().toString()) {
                case "char" -> "String.format(\"\\u%04X\", (int) " + fName + "))";
                case "String" -> "\"" + fName + "\"";
                default -> fName;
            };
            sj.add("\"" + fName + " = \" + " + fValue);
        }
        sb.append(sj).append(" + \"]\";\n\t}");
        return sb.toString();
    }

    private static String buildToTable(VariableElement[] fields) {
        StringBuilder sb = new StringBuilder();
        StringJoiner keyJ = new StringJoiner(", ");
        StringJoiner rowJ = new StringJoiner(", ");
        Arrays.stream(fields)
              .map(VariableElement::getSimpleName)
              .map(Object::toString)
              .forEach(name -> {
                    keyJ.add("\"" + name + "\"");
                    rowJ.add(name);
                });
        String keys = keyJ.toString();
        String rows = rowJ.toString();
        sb.append(String.format("""
                                \t@Override public Object[][] toTable() {
                                \t\treturn new Object[][]{{%s},{%s}};
                                \t}""", keys, rows));
        return sb.toString();
    }

    /**
     * Generates getter and setter methods for the given array of fields.
     * This method returns an array of strings with the generated methods.
     * <ol>
     *     <li>The first element contains the getter methods for the POJO class.</li>
     *     <li>The second element contains the setter methods for the POJO class.</li>
     *     <li>The third element contains the getter methods for the VO (Value Object) class.</li>
     * </ol>
     *
     * @param fields the array of fields to generate getter and setter methods for
     * @return an array of strings with the generated methods
     */
    private static String[] buildEtters(VariableElement[] fields) {
        StringBuilder gettersPOJO = new StringBuilder();
        StringBuilder gettersVO = new StringBuilder();
        StringBuilder setters = new StringBuilder();

        for (VariableElement field : fields) {
            String fieldName = field.getSimpleName().toString();
            String capitalizedFieldName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
            String fieldType = field.asType().toString();
            String getterPrefix = (fieldType.contains("boolean") || fieldType.contains("Boolean")) ? "is" : "get";

            gettersPOJO.append("\t@Override public ")
                    .append(String.format("%s %s%s() { return this.%s; }\n",
                            fieldType, getterPrefix, capitalizedFieldName, fieldName));

            gettersVO.append(String.format("\t%s %s%s();\n",
                            fieldType, getterPrefix, capitalizedFieldName));

            setters.append("\tpublic ")
                    .append(String.format("void set%s(%s %3$s) { this.%3$s = %3$s; }\n",
                            capitalizedFieldName, fieldType, fieldName));
        }
        return new String[]{gettersPOJO.toString(), setters.toString(), gettersVO.toString()};
    }

    /**
     * Generates a string containing private fields declaration for the given array of Field objects.
     *
     * @param fields the array of Field objects
     * @return a string containing private fields declaration
     */
    private static String buildPrivateFields(VariableElement[] fields) {
        return Arrays.stream(fields)
                     .map(field -> String.format("\tprivate %s %s;",
                                                 field.asType().toString(), field.getSimpleName()))
                     .collect(Collectors.joining("\n"));
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        roundEnv.getElementsAnnotatedWith(Data.class).stream()
                .filter(element -> element.getKind().isClass())
                .map(TypeElement.class::cast)
                .forEach(this::generate);
        return true;
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        messager = processingEnv.getMessager();
        filer = this.processingEnv.getFiler();
    }

    private static String chop(String s) {
        return Optional.ofNullable(s)
                       .filter(str -> str.length() != 0)
                       .map(str->str.replace("_",""))
                       .orElse(s);
    }

    private void generate(TypeElement typeElement) {
        String tableName = typeElement.getAnnotation(Data.class).value();
        String simpleName = chop(typeElement.getSimpleName().toString());
        String classPOJOName = chop(typeElement.getQualifiedName().toString());
        String classVOName = classPOJOName.replace(simpleName, "VO" + simpleName);
        String packageName = classPOJOName.substring(0, classPOJOName.lastIndexOf('.'));

        VariableElement[] fields = typeElement.getEnclosedElements().stream()
                                              .filter(e -> e.getKind().isField() && !e
                                                      .getModifiers()
                                                      .contains(Modifier.STATIC))
                                              .map(VariableElement.class::cast)
                                              .toArray(VariableElement[]::new);

        String privateFields = buildPrivateFields(fields);
        String[] etters = buildEtters(fields);
        String constructor = buildConstructor(simpleName, fields);
        String toString = buildToString(simpleName, fields);
        String toTable = buildToTable(fields);
        try {
            var file = filer.createSourceFile(classPOJOName);
            var fileVO = filer.createSourceFile(classVOName);

            try (var outVO = new PrintWriter(fileVO.openWriter())) {
                outVO.print("""
                               package %s;
                               
                               import javax.annotation.processing.Generated;
                               import org.uniandes.isis2304.utils.Tabulable;
                               
                               @Generated("HeaderProcessor[VO]")
                               public interface VO%s extends Tabulable {
                               %s
                               \t@Override Object[][] toTable();
                               \t@Override String toString();
                               }
                               """.formatted(packageName, simpleName, etters[2]));
            }

            try (var outPOJO = new PrintWriter(file.openWriter())) {
                outPOJO.print("""
                                  package %1$s;
                                                                        
                                  import javax.annotation.processing.Generated;
                                  import org.uniandes.isis2304.utils.TextTable;
                                  import org.uniandes.isis2304.core.Table;
                                                                        
                                  @Generated("HeaderProcessor[POJO]") @Table("%2$s")
                                  public class %3$s implements VO%3$s {
                                  \t//Generated fields
                                  %4$s
                                                                        
                                  \t//Generated constructor
                                  %5$s
                                                                        
                                  \t//Generated getters
                                  %6$s
                                                                        
                                  \t//Generated setters
                                  %7$s
                                  
                                  \t//Generated toTable
                                  %9$s
                                                                        
                                  \t//Generated toString
                                  %8$s
                                  }
                                  """.formatted(packageName, tableName, simpleName, privateFields, constructor, etters[0], etters[1], toString, toTable));
            }
        } catch (IOException e) {
            messager.printMessage(Diagnostic.Kind.ERROR, e.getMessage());
            e.printStackTrace();
        }
    }
}