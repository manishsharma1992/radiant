import { Component, ElementRef, Input, OnInit, Renderer2, ViewChild } from '@angular/core';

@Component({
  selector: 'app-progressbar',
  templateUrl: './progressbar.component.html',
  styleUrls: ['./progressbar.component.scss']
})
export class ProgressbarComponent implements OnInit {

  @Input() capacity!: string;
  @ViewChild('progressBar') progressBar!: ElementRef;

  constructor(private renderer2: Renderer2) {
    
  }

  ngOnInit(): void {
    if(this.capacity) {
      this.capacity = '0%'
      this.renderer2.setAttribute(this.progressBar.nativeElement, 'aria-valuenow', '0');
    }
    else {
      this.capacity = this.capacity + '%';
      this.renderer2.setAttribute(this.progressBar.nativeElement, 'aria-valuenow', this.capacity);
    }
  }

}
