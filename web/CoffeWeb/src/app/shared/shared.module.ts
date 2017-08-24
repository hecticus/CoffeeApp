import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { FormsModule }  from '@angular/forms';

import { TableBasicComponent } from 'app/shared/table-ns/table-ns-basic/table-ns-basic.component';
import { TableMenuComponent } from './table-ns/table-ns-menu/table-ns-menu.component';
import { PaginationComponent } from './pagination/pagination.component';
import { PagerService } from './pagination/pagination.service';
import { ContextMenuComponent } from 'angular2-contextmenu/src/contextMenu.component';

import { ToolComponent } from './tool-ns/tool-ns.component';

import { ConfirmationComponent } from './confirmation-ns/confirmation-ns.component';

import { ButtonIconComponent } from './button/button-icon/button-icon.component';
import { ButtonIconTextComponent } from './button/button-icon-text/button-icon-text.component';
import { ButtonTextComponent } from './button/button-text/button-text.component';
import { ButtonToogleComponent } from './button/button-toogle/button-toogle.component';
import { ButtonMediaComponent } from './button/button-media/button-media.component';

import { DynamicFormComponent } from './dynamic-form/dynamic-form.component';
import { DynamicFormQuestionComponent } from './dynamic-form/dynamic-form-question.component';
import { DropdownMultiComponent } from './dynamic-form/dropdown-multi-ns/dropdown-multi-ns.component';
import { MyDatePickerModule } from './dynamic-form/my-date-picker/my-date-picker.module';

import { DynamicShowComponent } from './dynamic-show/dynamic-show.component';
import { DynamicShowSmallComponent } from './dynamic-show/dynamic-show-small/dynamic-show-small.component';
import { TextboxClickComponent } from './dynamic-show/textbox-click/textbox-click.component';

import { FilterComponent } from './filter-ns/filter-ns.component';

import { DragDropDirective } from './directives/drag-drop.directive';

/*import { MediaChooseComponent } from './media-upload/media-choose/media-choose.component';
import { GalleryBasicComponent } from './gallery/gallery-basic/gallery-basic.component';
import { GalleryManagementComponent } from './gallery/gallery-management/gallery-management.component';
import { GalleryExpandedComponent } from './gallery/gallery-expanded/gallery-expanded.component';
import { MediaVideoComponent } from './media-video/media-video.component';
import { ScreenGrabComponent } from './media-video/screen-grab/screen-grab.component';
import { VideoListenerDirective } from './directives/video-listener.directive';
import { MediaUploadComponent } from './media-upload/media-upload.component';
import { MediaSingleExpandedComponent } from './media-upload/media-single-expanded/media-single-expanded.component';
import { MediaPdfManagementComponent } from './media-pdf-management/media-pdf-management.component';
*/
@NgModule({
	imports: [
		CommonModule,
		ReactiveFormsModule,
		FormsModule,
		MyDatePickerModule,
	],
	declarations: [
		TableBasicComponent,
		TableMenuComponent,
		PaginationComponent,
		ContextMenuComponent,

		ToolComponent,

		ConfirmationComponent,

		ButtonIconComponent,
		ButtonIconTextComponent,
		ButtonTextComponent,
		ButtonToogleComponent,
		ButtonMediaComponent,

		DynamicFormComponent,
		DynamicFormQuestionComponent,
		DropdownMultiComponent,

		DynamicShowComponent,
		DynamicShowSmallComponent,
		TextboxClickComponent,

		FilterComponent,

		DragDropDirective,
	/*	VideoListenerDirective,

		MediaUploadComponent,
		MediaChooseComponent,

		GalleryBasicComponent,
		GalleryManagementComponent,
		GalleryExpandedComponent,
		MediaVideoComponent,
		ScreenGrabComponent,
		MediaSingleExpandedComponent,
		MediaPdfManagementComponent*/
	],
	exports: [
		TableBasicComponent,
		TableMenuComponent,
		PaginationComponent,
		ContextMenuComponent,

		ToolComponent,

		ConfirmationComponent,

		ButtonIconComponent,
		ButtonIconTextComponent,
		ButtonTextComponent,
		ButtonToogleComponent,

		DynamicFormComponent,
		DynamicFormQuestionComponent,
		DropdownMultiComponent,

		DynamicShowComponent,
		DynamicShowSmallComponent,
		TextboxClickComponent,

		FilterComponent,

		/*GalleryBasicComponent,
		GalleryManagementComponent,
		MediaVideoComponent,
		MediaUploadComponent,
		MediaPdfManagementComponent*/
	],
	providers: [
		PagerService
	]
})
export class SharedModule { }
