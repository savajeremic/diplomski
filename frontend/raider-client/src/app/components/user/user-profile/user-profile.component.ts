import { Component, OnInit, Input, Output, EventEmitter, ViewChild, ElementRef } from '@angular/core';
import { ImageCroppedEvent } from 'ngx-image-cropper';
import { UserService } from '@/services/user.service';
import { AlertService } from '@/services/alert.service';
import { User } from '@/models/user';
import { AuthService } from '@/services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.scss']
})
export class UserProfileComponent implements OnInit {
  currentUser: User;
  isSubmitted = false;
  loading = false;
  imageChangedEvent: any = '';
  croppedImage: any = '';
  imageFile: any = '';
  @Output() updateAvatar: EventEmitter<any>;
  @Input() userId: number;
  @ViewChild('avatarModal', { static: false }) avatarModal: ElementRef;

  constructor(private userService: UserService,
    private alertService: AlertService,
    private authService: AuthService,
    private router: Router) {
    this.authService.getCurrentUserSubject.subscribe(value => this.currentUser = value);
    this.updateAvatar = new EventEmitter();
  }

  ngOnInit() {
  }

  uploadAvatar() {
    let croppedAvatar = this.croppedImage;

    if (!croppedAvatar) {
      this.alertService.error('First select a PNG image from your computer.');
      return;
    }

    const avatar = new FormData();
    avatar.append("avatar", this.dataURItoBlob(croppedAvatar));
    this.userService.uploadAvatar(avatar, this.userId).subscribe(
      data => {
        if (data.status === 200) {
          this.alertService.success('Profile image uploaded successfull!', true);
          if(!this.authService.isAdmin() || (this.authService.isAdmin() && this.userId == this.currentUser.id)) {
            this.currentUser.avatar = this.userService.sanitize(data.result);
            localStorage.setItem('currentUser', JSON.stringify(this.currentUser));
            this.updateAvatar.emit(this.currentUser.avatar);
          } else {
            this.updateAvatar.emit(data.result.avatar);
            this.router.navigate(['/admin/users']);
          }
          this.avatarModal.nativeElement.click();
        } else {
          this.alertService.error(data.message);
        }
      },
      error => {
        this.alertService.error(error);
      }
    );
  }

  dataURItoBlob(dataURI) {
    var byteString = atob(dataURI.split(',')[1]);
    var mimeString = dataURI.split(',')[0].split(':')[1].split(';')[0]
    var ab = new ArrayBuffer(byteString.length);
    var ia = new Uint8Array(ab);
    for (var i = 0; i < byteString.length; i++) {
      ia[i] = byteString.charCodeAt(i);
    }
    var blob = new Blob([ab], { type: mimeString });
    return blob;

  }

  fileChangeEvent(event: any): void {
    this.imageChangedEvent = event;
  }
  imageCropped(event: ImageCroppedEvent) {
    this.croppedImage = event.base64;


  }
  imageLoaded() {
    console.log("Image loaded.");
  }
  cropperReady() {
    console.log("Cropper ready.");
  }
  loadImageFailed() {
    console.log("Wrong file type.");
  }

}
