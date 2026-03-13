CREATE TABLE ping_log (
  id        BIGSERIAL     PRIMARY KEY,
  pinged_at TIMESTAMPTZ   NOT NULL DEFAULT NOW(),
  message   VARCHAR(255)
);
