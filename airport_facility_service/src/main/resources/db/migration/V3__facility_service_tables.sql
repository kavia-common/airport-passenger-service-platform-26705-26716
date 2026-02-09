-- Flyway migration V3 (airport_facility_service): service-local schema extensions.
-- The shared platform schema (V1/V2) already includes facilities/bookings/feedback/grievances tables.
-- Keep this migration in place to allow service-specific additions without modifying shared migrations.

-- Beacon-based offers / proximity promotions (service-local).
CREATE TABLE IF NOT EXISTS beacon_offers (
  id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
  beacon_id varchar(80) NOT NULL,
  title varchar(200) NOT NULL,
  message text NOT NULL,
  start_at timestamptz,
  end_at timestamptz,
  is_active boolean NOT NULL DEFAULT true,
  metadata jsonb,
  created_at timestamptz NOT NULL DEFAULT now(),
  updated_at timestamptz NOT NULL DEFAULT now()
);

CREATE INDEX IF NOT EXISTS idx_beacon_offers_beacon_active
  ON beacon_offers (beacon_id, is_active);
