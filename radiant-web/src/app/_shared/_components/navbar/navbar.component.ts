import { Component, ElementRef, OnInit } from '@angular/core';
import { Location } from '@angular/common';
import { TokenService } from '../../_services/token.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit {

  username!: string;

  constructor(
    private tokenService: TokenService,
    private router: Router) {}

  ngOnInit(): void {
    this.username = this.tokenService.getUser().displayName;
  }

  logout():void {
    this.tokenService.signOut();
    window.location.reload();
  }

}
