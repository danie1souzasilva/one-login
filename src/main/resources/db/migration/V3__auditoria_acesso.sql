CREATE TABLE IF NOT EXISTS auditoria_acesso (
    id UUID PRIMARY KEY,
    usuario_id UUID,
    empresa_id UUID,
    tipo_acesso VARCHAR(50) NOT NULL,
    endpoint TEXT NOT NULL,
    timestamp TIMESTAMP NOT NULL,
    ip_requisicao TEXT
);

CREATE INDEX IF NOT EXISTS idx_auditoria_usuario ON auditoria_acesso(usuario_id);
CREATE INDEX IF NOT EXISTS idx_auditoria_empresa ON auditoria_acesso(empresa_id);
