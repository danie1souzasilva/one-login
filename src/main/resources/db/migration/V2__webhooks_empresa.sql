CREATE TABLE IF NOT EXISTS webhooks_empresa (
    id UUID PRIMARY KEY,
    empresa_id UUID NOT NULL,
    url TEXT NOT NULL,
    eventos_assinados TEXT NOT NULL,
    criado_em TIMESTAMP NOT NULL,
    CONSTRAINT fk_webhook_empresa FOREIGN KEY (empresa_id) REFERENCES companies(id)
);

CREATE INDEX IF NOT EXISTS idx_webhooks_empresa_id ON webhooks_empresa(empresa_id);
