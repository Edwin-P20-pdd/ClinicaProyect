import { TestBed } from '@angular/core/testing';

import { CitaService } from './cita';
import { servicesVersion } from 'typescript';

describe('Cita', () => {
  let service: CitaService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CitaService);
  });

  it('should be created', () => {
    expect(servicesVersion).toBeTruthy();
  });
});
