ALTER TABLE prices ADD COLUMN description TEXT;

ALTER TABLE payments DROP CONSTRAINT payments_ticket_type_fkey;
ALTER TABLE payments ADD FOREIGN KEY (ticket_type) REFERENCES prices (ticket_type) ON UPDATE CASCADE;

UPDATE prices
SET ticket_type = 'Intero'
WHERE ticket_type = 'intero';
UPDATE prices
SET ticket_type = 'Ridotto'
WHERE ticket_type = 'ridotto';
UPDATE prices
SET ticket_type = 'Ridotto universitari'
WHERE ticket_type = 'ridotto universitari';
UPDATE prices
SET ticket_type = 'Intero 3D'
WHERE ticket_type = 'intero 3D';
UPDATE prices
SET ticket_type = 'Ridotto 3D'
WHERE ticket_type = 'ridotto 3D';
UPDATE prices
SET ticket_type = 'Ridotto universitari 3D'
WHERE ticket_type = 'ridotto universitari 3D';

UPDATE prices
SET description = 'Adulti con età superiore a 16 anni'
WHERE ticket_type = 'Intero';
UPDATE prices
SET description = 'Bambini con età inferiore a 16 anni'
WHERE ticket_type = 'Ridotto';
UPDATE prices
SET description = 'Riduzione per studenti universitari'
WHERE ticket_type = 'Ridotto universitari';
UPDATE prices
SET description = 'Adulti con età superiore a 16 anni'
WHERE ticket_type = 'Intero 3D';
UPDATE prices
SET description = 'Bambini con età inferiore a 16 anni'
WHERE ticket_type = 'Ridotto 3D';
UPDATE prices
SET description = 'Riduzione per studenti universitari'
WHERE ticket_type = 'Ridotto universitari 3D';
