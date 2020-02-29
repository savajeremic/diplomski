import { Component, OnInit } from '@angular/core';
import { AlertService } from '@/services/alert.service';

@Component({
  selector: 'app-contact',
  templateUrl: './contact.component.html',
  styleUrls: ['./contact.component.scss']
})
export class ContactComponent implements OnInit {

  constructor(private alertService: AlertService) { }

  ngOnInit() {
  }

  send() {
    this.alertService.success("Message successfully sent to the support team!", true);
  }

}
