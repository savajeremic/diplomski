import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.scss']
})
export class SearchComponent implements OnInit {

  @Input() searchText: string = "";
  @Output() searchTextChange: EventEmitter<String> = new EventEmitter();
  @Input() loading: EventEmitter<boolean> = new EventEmitter();

  constructor() { }

  ngOnInit() {
  }
}
