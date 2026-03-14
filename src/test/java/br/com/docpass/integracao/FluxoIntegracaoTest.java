package br.com.docpass.integracao;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Files;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
class FluxoIntegracaoTest {

    @Test
    @TestSecurity(user = "usuario", roles = {"user"})
    void deveCriarUsuario() {
        Map<String, Object> payload = Map.of(
                "dadosBasicos", Map.of(
                        "nomeCompleto", "Maria",
                        "email", "maria@example.com",
                        "cpf", "11144477735",
                        "telefone", "11999998888",
                        "dataNascimento", "1995-02-10"
                )
        );

        given()
                .contentType(ContentType.JSON)
                .body(payload)
        .when()
                .post("/api/v1/usuarios")
        .then()
                .statusCode(201)
                .body("id", notNullValue());
    }

    @Test
    @TestSecurity(user = "usuario", roles = {"user"})
    void deveEnviarDocumentoEConcederConsentimentoEAcessarApiEmpresa() throws Exception {
        Map<String, Object> usuario = Map.of(
                "dadosBasicos", Map.of(
                        "nomeCompleto", "Joao",
                        "email", "joao@example.com",
                        "cpf", "39053344705",
                        "telefone", "11988887777",
                        "dataNascimento", "1994-01-01"
                )
        );

        String usuarioId = given()
                .contentType(ContentType.JSON)
                .body(usuario)
        .when()
                .post("/api/v1/usuarios")
        .then()
                .statusCode(201)
                .extract().path("id");

        File arquivo = File.createTempFile("docpass", ".pdf");
        Files.writeString(arquivo.toPath(), "conteudo");

        given()
                .multiPart("usuarioId", usuarioId)
                .multiPart("tipoDocumento", "RG")
                .multiPart("arquivo", arquivo, "application/pdf")
        .when()
                .post("/api/v1/documentos/enviar")
        .then()
                .statusCode(200)
                .body("usuarioId", equalTo(usuarioId));

        Map<String, Object> empresaPayload = Map.of("nome", "EmpresaX");
        io.restassured.path.json.JsonPath empresaJson = given()
                .contentType(ContentType.JSON)
                .body(empresaPayload)
        .when()
                .post("/api/v1/empresas")
        .then()
                .statusCode(201)
                .extract().jsonPath();

        String chaveApi = empresaJson.getString("chaveApi");
        String empresaId = empresaJson.getString("id");

        Map<String, Object> consentimento = Map.of(
                "usuarioId", usuarioId,
                "empresaId", empresaId,
                "escopos", java.util.List.of("PERFIL_BASICO", "DOCUMENTOS")
        );

        given()
                .contentType(ContentType.JSON)
                .body(consentimento)
        .when()
                .post("/api/v1/consentimentos")
        .then()
                .statusCode(201);

        given()
                .header("X-API-KEY", chaveApi)
        .when()
                .get("/api-empresas/v1/perfil/" + usuarioId)
        .then()
                .statusCode(200)
                .body("id", equalTo(usuarioId));
    }
}
