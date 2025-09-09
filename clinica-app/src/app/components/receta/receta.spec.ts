import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RecetaComponent } from './receta';

describe('Receta', () => {
  let component: RecetaComponent;
  let fixture: ComponentFixture<RecetaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [RecetaComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RecetaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
