import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';
import { AlertService } from '@/services/alert.service';

@Component({
  selector: 'app-alert',
  templateUrl: './alert.component.html',
  styleUrls: ['./alert.component.scss'],
})
export class AlertComponent implements OnInit, OnDestroy {

  private subscription: Subscription;
  message: any;
  title: string = "Oops!";
  isShown: boolean = true;

  constructor(private alertService: AlertService) { }

  ngOnInit() {
    this.subscription = this.alertService.getAlert()
      .subscribe(message => {
        this.isShown = true;
        switch (message && message.type) {
          case 'success':
            message.cssClass = 'alert alert-success alert-box-all alert-dismissible fade show';
            this.title = "Success!";
            break;
          case 'error':
            message.cssClass = 'alert alert-danger alert-box-all alert-dismissible fade show';
            this.title = "Oops!";
            break;
        }
        this.message = message;
      });
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

}
